export class PersonDraft {

    firstName: string = null;
  
    lastName: string = null;

    address: string[] = [];
  
    private notNullOrEmpty(str: string): boolean {
      return typeof str === "string" && str !== "";
    }
  
    isValid(): boolean {
      return this.notNullOrEmpty(this.firstName) &&
        this.notNullOrEmpty(this.lastName);
    }
  }
  