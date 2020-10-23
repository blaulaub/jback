import { VerificationByPassword } from '../verification-means/verification-by-password';

export class LoginData {

  userIdentification = '';

  verificationMean = new VerificationByPassword();
}
