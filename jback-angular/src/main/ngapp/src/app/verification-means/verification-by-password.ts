import { VerificationMean } from './verification-mean';

export class VerificationByPassword extends VerificationMean {

  readonly type = 'password';

  username = '';

  password = '';
}
