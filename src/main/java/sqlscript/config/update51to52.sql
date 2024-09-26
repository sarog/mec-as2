#
# Update script db version 51 to db version 52
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE notification ADD COLUMN notifyclientserver INTEGER DEFAULT 0 NOT NULL


