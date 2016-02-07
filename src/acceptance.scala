package org.specs2
package robo

trait AcceptanceBase
extends Specification
with Base

trait IsoSpec
extends AcceptanceBase
with IsoBase

trait ClassSpec
extends AcceptanceBase
with ClassBase
