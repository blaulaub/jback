import { VerificationMean } from './verification-mean';

export class VerificationByEmail extends VerificationMean {

  readonly type = 'email';

  emailAddress = '';
}
