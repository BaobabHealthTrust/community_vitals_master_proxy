P.1. Site Setup [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:setupSite()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Target Site Username [pos => 0 $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]

Q.1.2. Target Site Password [pos => 1 $$ field_type => password $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]

Q.1.3. Target Server IP Address [pos => 2 $$ field_type => number $$ validationRule => ^\d+\.\d+\.\d+\.\d+$ $$ validationMessage => Wrong value. Expecting an IP address of format xxx.xxx.xxx.xxx!]

Q.1.4. Site Code [pos => 3]

Q.1.5. ID Batch Size [pos => 4 $$ field_type => number $$ validationRule => ^\d+$ $$ validationMessage => Wrong value. Expecting whole numbers!]
