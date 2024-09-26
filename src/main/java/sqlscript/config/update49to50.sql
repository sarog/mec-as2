#
# Update script db version 49 to db version 50
# custom parmeter for the auth process
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE oauth2 ADD COLUMN customparamauth VARCHAR(1024)
ALTER TABLE oauth2 ADD COLUMN pkce INTEGER DEFAULT 0 NOT NULL
ALTER TABLE oauth2 ADD COLUMN pkceverifier VARCHAR(128) DEFAULT '_new' not NULL

ALTER TABLE partner ADD COLUMN useoauth2message INTEGER DEFAULT 0 NOT NULL
ALTER TABLE partner ADD COLUMN useoauth2mdn INTEGER DEFAULT 0 NOT NULL
ALTER TABLE partner ADD COLUMN oauth2idmessage INTEGER
ALTER TABLE partner ADD FOREIGN KEY (oauth2idmessage) REFERENCES oauth2(id); 
ALTER TABLE partner ADD COLUMN oauth2idmdn INTEGER
ALTER TABLE partner ADD FOREIGN KEY (oauth2idmdn) REFERENCES oauth2(id); 
#drop cols
ALTER TABLE partner DROP COLUMN usesmtpoauth2message
ALTER TABLE partner DROP COLUMN usesmtpoauth2mdn
#do not drop the foreign key, just set its referenced columns to NULL which will disable the foreign key
UPDATE partner SET smtpoauth2idmessage=NULL
UPDATE partner SET smtpoauth2idmdn=NULL



