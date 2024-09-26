#
# Update script db version 49 to db version 50
# This modifies the message table structure and adds a secureconnection flag
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE messages ADD COLUMN secureconnection INT DEFAULT 0 NOT NULL


