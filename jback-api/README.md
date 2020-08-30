# About

This module defines and implements a REST-API to jback-core
(for business concerns) and jback-sec (for security concerns).

## Design goals

* only add a thin layer defining the API, being easily replaceable
  by another API or another API technology
* tie together the modules that separately implement the
  business and security logic 
* leave all business and security logic in the respective modules,
  do not add extra business or security logic

## Dependencies

Builds on jpa-core and jpa-sec.
