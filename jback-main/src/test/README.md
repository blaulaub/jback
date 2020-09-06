# Requirements

## §1 Anyone can register

Anyone can `POST` a registration to `/api/v1/registration`.

### Definition

The caller will be provided with some registration code that then can be
`PUT` to `/api/v1/registration/{id}` with `{id}` being the ID of the
pending registration.

As a result, the user will be authenticated and sufficiently authorized to
create one or more persons.

### Tests

* [AnybodyCanRegister](java/ch/patchcode/jback/main/session/AnybodyCanRegister.java)
  ensures that registration works
* [SomebodyRegisteredCanCreatePerson](java/ch/patchcode/jback/main/persons/SomebodyRegisteredCanCreatePerson.java)
  ensures that a registered user can create a person