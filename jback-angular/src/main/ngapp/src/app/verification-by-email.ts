import { VerificationMean } from './verification-mean'

export class VerificationByEmail implements VerificationMean {
  readonly type = "Email";
  emailAddress = "";
}
