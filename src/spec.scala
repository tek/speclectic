package org.specs2
package robo

import specification.{Context, Around}
import specification.core.{
  SpecificationStructure, SpecStructure, Env, Fragments
}
import specification.create.{
  ContextualFragmentFactory, SpecificationCreation, FragmentsFactory
}
import data.AlwaysTag
import execute.{AsResult, Result}

/**
 * This class switches the class instantiated by specs2 with one created by
 * Robolectric's class loader.
 * The function @structure is accessed by the specs2 runner to obtain the
 * testable Fragments, after which this Specification instance is discarded.
 * The RoboSuiteRunner creates a copy of this instance using the RL class
 * loader and its @structure function is returned.
 * To ensure that the RSR with the original class loader is used to teardown
 * the Robolectric universe, the new instances runner attribute is updated.
 * There is no way around this, because the S2StringContextCreation trait
 * obtains the @fragmentFactory in the constructor, which attaches the
 * instrumented instance irrevocably to the fragments, including the around
 * hook where the teardown is performed.
 */
trait Base
extends robotest.RoboTest
with FragmentsFactory
with SpecificationStructure
with SpecificationCreation
{
  private[robo] var runner = new RoboSuiteRunner(this)

  private def instrumentedInstance = runner.roboInstance

  override def structure: Env ⇒ SpecStructure = {
    instrumentedInstance.runner = runner
    instrumentedInstance.instrumentedStructure
  }

  def instrumentedStructure = super.structure

  override def map(is: SpecStructure): SpecStructure = {
    sequential ^ is
  }

  private[robo] def setup() = runner.setup()

  private[robo] def teardown() = runner.teardown()
}

trait IsoBase
extends Base
{
  private def setupUniverse[R: AsResult](r: ⇒ R): Result = {
    setup()
    try AsResult(r)
    finally teardown()
  }

  protected def setupUniverseContext: Env ⇒ Context =
    env ⇒ new Around { def around[R : AsResult](r: ⇒ R) = setupUniverse(r) }

  override protected def fragmentFactory = {
    new ContextualFragmentFactory(super.fragmentFactory, setupUniverseContext)
  }
}

trait ClassBase
extends Base
{
  override def map(fs: ⇒ Fragments) = {
  def step[T](t: ⇒ T) = fragmentFactory.step(t)
  val tag = fragmentFactory.markAs(AlwaysTag)
  super
    .map(fs)
    .prepend(Seq(step(setup()), tag))
    .append(Seq(step(teardown()), tag))
  }
}

class RoboSuiteRunner(val suite: Base)
extends robotest.RoboSuiteRunner[Base]
