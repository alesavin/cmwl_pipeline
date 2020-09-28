package cromwell.pipeline.datastorage.dao.repository.utils

import cats.implicits._
import cromwell.pipeline.datastorage.dto.ProjectAdditionRequest
import cromwell.pipeline.datastorage.dto.auth.{SignInRequest, SignUpRequest}
import cromwell.pipeline.datastorage.dto.user.{PasswordUpdateRequest, UserUpdateRequest}
import cromwell.pipeline.model.validator.Enable
import cromwell.pipeline.model.wrapper.{Name, Password, UserEmail}
import org.scalacheck.Gen

import scala.util.Random

object GeneratorUtils {
  private def stringGen(n: Int): Gen[String] = Gen.listOfN(n, Gen.alphaLowerChar).map(_.mkString)

  private lazy val emailGen: Gen[UserEmail] =
    for {
      name <- stringGen(10)
      mail <- Gen.oneOf(Mail.values.toSeq)
      domain <- Gen.oneOf(Domain.values.toSeq)
    } yield UserEmail(s"$name@$mail.$domain", Enable.Unsafe)

  private lazy val nameGen: Gen[Name] = for {
    name <- stringGen(6)
  } yield Name(name, Enable.Unsafe)

  private lazy val passwordGen: Gen[Password] = for {
    upperCase <- Gen.alphaUpperStr.suchThat(s => s.nonEmpty)
    lowerLetters <- stringGen(10)
    digits <- Gen.posNum[Int]
    symbol <- Gen.oneOf(Symbol.values.toSeq)
  } yield {
    val password = Random.shuffle(List(upperCase, lowerLetters, digits.toString, symbol.toString).flatten).mkString
    Password(password, Enable.Unsafe)
  }

  lazy val userUpdateRequestGen: Gen[UserUpdateRequest] = for {
    email <- emailGen
    firstName <- nameGen
    lastName <- nameGen
  } yield UserUpdateRequest(email, firstName, lastName)

  lazy val passwordUpdateRequestGen: Gen[PasswordUpdateRequest] = for {
    currentPassword <- passwordGen
    newPassword <- passwordGen
  } yield PasswordUpdateRequest(currentPassword, newPassword, newPassword)

  lazy val signUpRequestGen: Gen[SignUpRequest] = for {
    email <- emailGen
    password <- passwordGen
    firstName <- nameGen
    lastName <- nameGen
  } yield SignUpRequest(email, password, firstName, lastName)

  lazy val signInRequestGen: Gen[SignInRequest] = for {
    email <- emailGen
    password <- passwordGen
  } yield SignInRequest(email, password)

  lazy val projectAdditionRequest: Gen[ProjectAdditionRequest] = stringGen(10).map(ProjectAdditionRequest(_))

  object Mail extends Enumeration {
    val Mail, Gmail, Epam, Yandex = Value
  }

  object Domain extends Enumeration {
    val Org, Com, Ru, It, Cz = Value
  }

  object Symbol extends Enumeration {
    val $, %, & = Value
  }
}
