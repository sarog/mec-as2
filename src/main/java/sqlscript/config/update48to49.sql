#
# Update script db version 48 to db version 49
# All the oauth accounts and create possible reference in the partner table
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE partner ADD COLUMN usesmtpoauth2message INTEGER DEFAULT 0 NOT NULL
ALTER TABLE partner ADD COLUMN usesmtpoauth2mdn INTEGER DEFAULT 0 NOT NULL
ALTER TABLE partner ADD COLUMN smtpoauth2idmessage INTEGER
ALTER TABLE partner ADD COLUMN smtpoauth2idmdn INTEGER

