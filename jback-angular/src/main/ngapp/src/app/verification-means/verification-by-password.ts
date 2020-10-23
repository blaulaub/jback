import { VerificationMean } from './verification-mean';

export class VerificationByPassword extends VerificationMean {

  readonly type = 'password';

  username = '';

  password = '';

  isValid(): boolean {
    return typeof this.username === 'string' && this.username !== '' &&
    typeof this.password === 'string' && this.password !== '';
  }
}
