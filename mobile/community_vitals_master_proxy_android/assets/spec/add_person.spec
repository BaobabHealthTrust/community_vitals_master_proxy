P.1. Add Person [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:savePerson()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. First Name [pos => 0]

Q.1.2. Middle Name [pos => 1 $$ condition => (typeof(localStorage.askMiddleName) != "undefined" ? (localStorage.askMiddleName.toLowerCase() == "true" ? true : false ) : false ) == true]

Q.1.3. Last Name [pos => 2]

Q.1.4. Gender [pos => 3]
O.1.4.1. Male
O.1.4.2. Female

Q.1.5. Year of birth [pos => 4 $$ field_type => number $$ validationRule => ^\d{4}$|^Unknown$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.6. Age [pos => 5 $$ condition => __$("1.5").value.trim().toLowerCase() == "unknown" $$ field_type => number]

Q.1.7. Month of birth [pos => 6 $$ condition => __$("1.5").value.trim().toLowerCase() != "unknown"]
O.1.7.1. January
O.1.7.2. February
O.1.7.3. March
O.1.7.4. April
O.1.7.5. May
O.1.7.6. June
O.1.7.7. July
O.1.7.8. August
O.1.7.9. September
O.1.7.10. October
O.1.7.11. November
O.1.7.12. December
O.1.7.13. Unknown

Q.1.8. Date of birth [pos => 7 $$ condition => __$("1.7").value.trim().toLowerCase() != "unknown" && __$("1.5").value.trim().toLowerCase() != "unknown" $$ tt_onLoad => generateDays('1.5', '1.7') ]

Q.1.9. Occupation [pos => 8]
O.1.9.1. Driver
O.1.9.2. Housewife
O.1.9.3. Messenger
O.1.9.4. Business
O.1.9.5. Farmer
O.1.9.6. Salesperson
O.1.9.7. Teacher
O.1.9.8. Student
O.1.9.9. Security guard
O.1.9.10. Domestic worker
O.1.9.11. Police
O.1.9.12. Office worker
O.1.9.13. Preschool child
O.1.9.14. Mechanic
O.1.9.15. Prisoner
O.1.9.16. Craftsman
O.1.9.17. Healthcare Worker
O.1.9.18. Soldier
O.1.9.19. Other
O.1.9.20. Unknown

