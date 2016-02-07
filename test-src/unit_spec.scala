package speclectic

import org.specs2.robo.mutable.{IsoSpec, ClassSpec, UnitBase}

trait UnitSpecBase
extends UnitBase
with SpecBase
{
  s"$uni universes, unit style" >>
  {
    "insert" >> insert

    "query" >> query
  }
}

class IsoUnitSpec
extends IsoSpec
with UnitSpecBase
with IsoBase

class ClassUnitSpec
extends ClassSpec
with UnitSpecBase
with ClassBase
