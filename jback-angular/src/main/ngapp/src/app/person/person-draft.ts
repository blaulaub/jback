export class PersonDraft {

    firstName: string = null;
  
    lastName: string = null;

    address: string[] = [];
  
    private static notNullOrEmpty(str: string): boolean {
      return typeof str === "string" && str !== "";
    }
  
    static isValid(draft: PersonDraft): boolean {
      return PersonDraft.notNullOrEmpty(draft.firstName) &&
        PersonDraft.notNullOrEmpty(draft.lastName) &&
        draft.address.every(line => PersonDraft.notNullOrEmpty(line));
    }
  }
  