package cromwell.pipeline.datastorage.dao.repository.utils

import cromwell.pipeline.datastorage.dto.auth.{SignInRequest, SignUpRequest}
import cromwell.pipeline.datastorage.dto.user.{PasswordUpdateRequest, UserUpdateRequest}
import org.scalacheck.Arbitrary

object ArbitraryUtils {
  import GeneratorUtils._

  implicit def arbitraryUserUpdateRequest: Arbitrary[UserUpdateRequest] = Arbitrary(userUpdateRequestGen)
  implicit def arbitraryPasswordUpdateRequest: Arbitrary[PasswordUpdateRequest] = Arbitrary(passwordUpdateRequestGen)
  implicit def arbitrarySignUpRequest: Arbitrary[SignUpRequest] = Arbitrary(signUpRequestGen)
  implicit def arbitrarySignInRequest: Arbitrary[SignInRequest] = Arbitrary(signInRequestGen)
}
