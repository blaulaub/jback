export class VerificationCode {

  verificationCode: string = null;

  private notNullOrEmpty(str: string): boolean {
    return typeof str === 'string' && str !== '';
  }

  isValid(): boolean {
    return this.notNullOrEmpty(this.verificationCode);
  }
}
