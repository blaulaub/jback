export class PersonDraft {

  firstName: string = null;

  lastName: string = null;

  username: string = null;

  password: string = null;

  address: string[] = [];

  private static notNullOrEmpty(str: string): boolean {
    return typeof str === 'string' && str !== '';
  }

  static isValid(draft: PersonDraft): boolean {
    return PersonDraft.notNullOrEmpty(draft.firstName) &&
      PersonDraft.notNullOrEmpty(draft.lastName) &&
      PersonDraft.notNullOrEmpty(draft.username) &&
      PersonDraft.notNullOrEmpty(draft.password) &&
      draft.address.every(line => PersonDraft.notNullOrEmpty(line));
  }
}
