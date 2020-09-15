import { VerificationMean } from './verification-mean'

export class VerificationBySms implements VerificationMean {

  readonly type = "SMS";

  phoneNumber = "";

  isValid(): boolean {
    return typeof this.phoneNumber === "string" && this.phoneNumber !== "";
  }
}
