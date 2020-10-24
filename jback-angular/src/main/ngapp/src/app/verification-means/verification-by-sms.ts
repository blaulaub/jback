import { VerificationMean } from './verification-mean';

export class VerificationBySms extends VerificationMean {

  readonly type = 'sms';

  phoneNumber = '';
}
