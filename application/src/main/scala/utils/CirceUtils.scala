package utils

import play.api.http.{ContentTypeOf, Writeable}
import akka.util.ByteString
import io.circe._

object CirceUtils {
  implicit def jsonWriteable: Writeable[Json] = {
    Writeable.apply[Json](json => ByteString(json.spaces2))(ContentTypeOf(Some("application/json")))
  }
}
