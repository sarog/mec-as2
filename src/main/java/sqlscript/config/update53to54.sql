#
# Update script db version 53 to db version 54
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE partner ADD COLUMN overwritelocalsecurity INTEGER DEFAULT 0 NOT NULL


