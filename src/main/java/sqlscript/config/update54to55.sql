# Update script db version 54 to db version 55
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE keydata ADD COLUMN securityprovider VARCHAR(65)DEFAULT 'BC' NOT NULL 


