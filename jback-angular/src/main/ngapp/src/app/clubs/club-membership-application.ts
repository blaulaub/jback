import { Club } from './club';
import { Person } from '../person/person';

export interface ClubMembershipApplication {

  id: string;

  person: Person;

  club: Club;

}
