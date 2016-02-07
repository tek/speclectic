package org.specs2
package robo
package mutable

trait UnitBase
extends org.specs2.mutable.Specification
with Base

trait IsoSpec
extends UnitBase
with IsoBase

trait ClassSpec
extends UnitBase
with ClassBase
