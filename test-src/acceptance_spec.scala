package speclectic

import org.specs2.robo.{IsoSpec, ClassSpec, AcceptanceBase}

trait AccBase
extends AcceptanceBase
with SpecBase
{
  def is = s2"""
  $uni universes, acceptance style

  insert $insert
  query $query
  """
}

class IsoAccSpec
extends IsoSpec
with AccBase
with IsoBase

class ClassAccSpec
extends ClassSpec
with AccBase
with ClassBase
