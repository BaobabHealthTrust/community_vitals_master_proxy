P.1. Messages [program: COMMUNITY VITALS REGISTRATION]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries
Q.1.1. Select activity [pos => 0]
O.1.1.1. Send Message
O.1.1.2. Read Message
Q.1.2. Select Recipients [pos => 1 $$ condition => __$('lbl_1.1').value == 'Send Message' $$ multiple => multiple]
O.1.2.1. T/A Msundwe
O.1.2.2. T/A Chadza
O.1.2.3. T/A Kalolo
O.1.2.4. T/A Kapeni
O.1.2.5. T/A Kabudula
O.1.2.6. T/A Khongoni
O.1.2.7. T/A Mabuka
Q.1.3. Write Message [pos => 2 $$ field_type => textarea $$ condition => __$('lbl_1.1').value == 'Send Message']
Q.1.4. Send Message? [pos => 3 $$ condition => __$('lbl_1.1').value == 'Send Message']
O.1.4.1. Yes
O.1.4.2. No
Q.1.5. Messages List [pos => 4 $$ tt_onLoad => listMessages() $$ optional => true]
Q.1.6. Read Message [pos => 5 $$ tt_onLoad => displayMessage() $$ optional => true]
Q.1.7. Reply Message? [pos => 6]
O.1.7.1. Yes
O.1.7.2. No
Q.1.8. Write Message [pos => 7 $$ field_type => textarea $$ condition => __$('lbl_1.7').value == 'Yes']
Q.1.9. Send Message? [pos => 8 $$ condition => __$('lbl_1.7').value == 'Yes']
O.1.9.1. Yes
O.1.9.2. No
