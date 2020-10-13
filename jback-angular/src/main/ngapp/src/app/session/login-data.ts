import { VerificationByPassword } from './verification-by-password'

export class LoginData {

  userIdentification = "";

  verificationMean = new VerificationByPassword();
}
