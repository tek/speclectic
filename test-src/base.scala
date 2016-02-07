package speclectic

import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}
import android.content.ContentValues

import org.robolectric.RuntimeEnvironment

import org.specs2.specification.AroundEach
import org.specs2.execute.{Result, AsResult}
import org.specs2.matcher.MustMatchers

trait SpecBase
extends AroundEach
with MustMatchers
{
  lazy val helper =
    new SQLiteOpenHelper(RuntimeEnvironment.application, "test", null, 1) {
      override def onUpgrade(
        db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit = {}
      override def onCreate(db: SQLiteDatabase): Unit = {}
    }

  def db: SQLiteDatabase = helper.getWritableDatabase

  def around[R: AsResult](r: ⇒ R): Result = {
    initDb()
    try AsResult(r)
    finally finalizeDb()
  }

  def initDb() = {
    db.execSQL("CREATE TABLE Test(_id INTEGER PRIMARY KEY, value TEXT);")
  }

  def finalizeDb() = {
    db.execSQL("DROP TABLE IF EXISTS Test;")
    db.close()
    RuntimeEnvironment.application.getDatabasePath(helper.getDatabaseName)
      .delete()
  }

  def insertData() = {
    val values = new ContentValues()
    values.put("_id", Integer.valueOf(1))
    values.put("value", "test")
    db.insert("Test", null, values)
  }


  lazy val cursor = {
    db.query("Test", null, null, null, null, null, null)
  }

  def insert = insertData() must_== 1

  def query = cursor.moveToFirst() must beFalse and (cursor.getCount must_== 0)

  def uni: String
}

trait IsoBase
extends SpecBase
{
  def uni = "isolated example"
}

trait ClassBase
extends SpecBase
{
  def uni = "class level"
}
