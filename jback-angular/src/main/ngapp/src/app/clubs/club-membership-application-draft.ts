import { Club } from './club';
import { Person } from '../person/person';

export interface ClubMembershipAppicationDraft {

  person: Person;

  club: Club;

}
