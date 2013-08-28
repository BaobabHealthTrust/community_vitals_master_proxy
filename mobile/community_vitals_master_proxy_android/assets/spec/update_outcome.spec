P.1. Update Outcome [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:updateOutcome()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Person First Name [pos => 'a' $$ dynamicLoader => listFirstNames(__$('inputField').value.trim()) $$ tt_onLoad => listFirstNames('')]
C.1.1.1. First Name List

Q.1.2. Person Last Name [pos => 'b' $$ dynamicLoader => listLastNames(__$('inputField').value.trim()) $$ tt_onLoad => listLastNames('')]
C.1.2.1. Last Name List

Q.1.3. Person Gender [pos => 'c']
O.1.3.1. Male
O.1.3.2. Female

Q.1.4. Select Person From The Following: [pos => 'd' $$ tt_onLoad => getPeopleNames(__$('1.1').value.trim(), __$('1.2').value.trim(), __$('1.3').value.trim())]
C.1.4.1. List of matching people 

Q.1.5. Outcome [pos => 'e']
O.1.5.1. Dead
O.1.5.2. Transfer Out
O.1.5.3. Transfer Back

Q.1.6. Year of death [condition => __$('1.5').value.toLowerCase() == 'dead' $$ pos => 'f' $$ field_type => number]

Q.1.7. Month of death [condition => __$('1.5').value.toLowerCase() == 'dead' && __$("1.6").value.trim().toLowerCase() != "unknown" $$ pos => 'g']
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

Q.1.8. Day of death [condition => __$('1.5').value.toLowerCase() == 'dead' && __$("1.7").value.trim().toLowerCase() != "unknown" && __$("1.6").value.trim().toLowerCase() != "unknown" $$ pos => 'h' $$ tt_onLoad => generateDays('1.6', '1.7')]

Q.1.9. Cause of death [condition => __$('1.5').value.toLowerCase() == 'dead' $$ pos => 'i']
O.1.9.1. Natural causes
O.1.9.2. Accident
O.1.9.3. Old age
O.1.9.4. Sickness
O.1.9.5. Other

Q.1.10. Transfer out year [condition => __$('1.5').value.toLowerCase() == 'transfer out' $$ pos => 'j' $$ field_type => number]

Q.1.11. Transfer out month [condition => __$('1.5').value.toLowerCase() == 'transfer out' && __$("1.10").value.trim().toLowerCase() != "unknown" $$ pos => 'k']
O.1.11.1. January
O.1.11.2. February
O.1.11.3. March
O.1.11.4. April
O.1.11.5. May
O.1.11.6. June
O.1.11.7. July
O.1.11.8. August
O.1.11.9. September
O.1.11.10. October
O.1.11.11. November
O.1.11.12. December
O.1.11.13. Unknown

Q.1.12. Transfer out day [condition => __$('1.5').value.toLowerCase() == 'transfer out' && __$("1.10").value.trim().toLowerCase() != "unknown" && __$("1.11").value.trim().toLowerCase() != "unknown" $$ pos => 'l' $$ tt_onLoad => generateDays('1.10', '1.11')]

Q.1.13. Transfer back year [condition => __$('1.5').value.toLowerCase() == 'transfer back' $$ pos => 'm' $$ field_type => number]

Q.1.14. Transfer back month [condition => __$('1.5').value.toLowerCase() == 'transfer back' && __$("1.13").value.trim().toLowerCase() != "unknown" $$ pos => 'n']
O.1.14.1. January
O.1.14.2. February
O.1.14.3. March
O.1.14.4. April
O.1.14.5. May
O.1.14.6. June
O.1.14.7. July
O.1.14.8. August
O.1.14.9. September
O.1.14.10. October
O.1.14.11. November
O.1.14.12. December
O.1.14.13. Unknown

Q.1.15. Transfer back day [condition => __$('1.5').value.toLowerCase() == 'transfer back' && __$("1.13").value.trim().toLowerCase() != "unknown" && __$("1.14").value.trim().toLowerCase() != "unknown" $$ pos => 'o' $$ tt_onLoad => generateDays('1.13', '1.14')]


