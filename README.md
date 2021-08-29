
# Academia

Academia is a desktop application built using TornadoFX and Kotlin. Academia aids in the management of a university system using Object Oriented Programming and Design Patterns (MVC).

**Academia allows users to:**

- View details of people (both students and lecturers) and subjects
- Register lecturers and students for subjects

## Features

- Search for a person by name or ID
- View a persons details and subjects
- View subject details
- Register people for subjects
- Add new people
- Create new subjects 
- Closing off month (staff is paid and students pay their university fees)

## Tech Stack

**Client:** TornadoFX and Kotlin

## Documentation

**Bussiness rules:**

    1) A university consists of a name, a collection of people and a collection of subjects.
    2) Each subject has a name, a code, a lecturer, credits, hours per week, a price per month, and a collection of students.
    3) Each person can be assigned subjects either to attend or to teach.
    4) The university has a pool of funds. Fees are paid into this pool and salaries are paid from it.
    5) Administrative staff members have fixed salaries, which they are paid every month.
    6) Academic staff memebers have salaries based on the average number of hours they teach each month.
    7) Students pay fees based on the subjects for which they are registered
    8) At the end of every month, staff members are paid their salariee and students pay their fees.
    9) A student can't register for a new subject if it would cause their maximum credits to be exceeded.

**4 Types of people:**

- Diploma students, max 60 credits
- Degree students, max 180 credits
- Academic staff, are paid R400 per hour
- Admin staff, are paid R15000 per month
