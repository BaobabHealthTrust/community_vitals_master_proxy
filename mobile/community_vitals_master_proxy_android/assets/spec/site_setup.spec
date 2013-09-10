P.1. Site Setup [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:setupSite()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Target Site Username [pos => 0 $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]

Q.1.2. Target Site Password [pos => 1 $$ field_type => password $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]

Q.1.3. Target Server IP Address [pos => 2 $$ field_type => number $$ validationRule => ^\d+\.\d+\.\d+\.\d+$ $$ validationMessage => Wrong value. Expecting an IP address of format xxx.xxx.xxx.xxx!]

Q.1.4. Target Server IP Address Port [pos => 3 $$ field_type => number $$ validationRule => ^\d+$ $$ validationMessage => Wrong value. Expecting a number!]

Q.1.5. Site Code [pos => 4]

Q.1.6. ID Batch Size [pos => 5 $$ field_type => number $$ validationRule => ^\d+$ $$ validationMessage => Wrong value. Expecting whole numbers!]
