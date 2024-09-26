#
# Update script db version 50 to db version 51
# client credentials support RFC 6749 4.4
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE oauth2 ADD COLUMN rfc INTEGER DEFAULT 1 NOT NULL
ALTER TABLE oauth2 ADD COLUMN clientcredentialssecret INTEGER DEFAULT 1 NOT NULL


