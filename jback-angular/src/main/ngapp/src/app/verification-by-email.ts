import { VerificationMean } from './verification-mean'

export class VerificationByEmail extends VerificationMean {

  readonly type = "email";

  emailAddress = "";

  isValid(): boolean {
    return typeof this.emailAddress === "string" && this.emailAddress !== "";
  }
}
