P.1. Select Date [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:selectDate()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries


Q.1.1. Select Year [pos => 0 $$ field_type => number $$ validationRule => ^\d{4}$ $$ validationMessage => Wrong value. Expecting a 4 digit number!]

Q.1.2. Select Month [pos => 1 $$ tt_showToggleKeyboard => false $$ showKeys => false $$ dynamicLoader => loadMonths(__$('inputField').value.trim()) $$ tt_onLoad => loadMonths('') $$ condition => __$("1.1").value.trim().toLowerCase() != "unknown"]

Q.1.3. Select Date [pos => 3 $$ tt_customKeyboard => true $$ condition => __$("1.2").value.trim().toLowerCase() != "unknown" $$ tt_onLoad => generateDays('1.1', '1.2') ]


