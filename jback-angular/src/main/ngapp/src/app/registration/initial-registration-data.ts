import { VerificationMean } from '../verification-means/verification-mean';

export class InitialRegistrationData {

  firstName: string = null;

  lastName: string = null;

  verificationMean: VerificationMean = null;

  private notNullOrEmpty(str: string): boolean {
    return typeof str === 'string' && str !== '';
  }

  isValid(): boolean {
    return this.notNullOrEmpty(this.firstName) &&
      this.notNullOrEmpty(this.lastName) &&
      this.verificationMean instanceof VerificationMean &&
      this.verificationMean.isValid();
  }
}
