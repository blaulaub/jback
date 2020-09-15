import { VerificationMean } from './verification-mean'

export class VerificationBySms extends VerificationMean {

  readonly type = "sms";

  phoneNumber = "";

  isValid(): boolean {
    return typeof this.phoneNumber === "string" && this.phoneNumber !== "";
  }
}
