#
# Update script db version 52 to db version 53
# $Author: heller $
# $Revision: 1.2 $
#
#
CREATE TABLE keydata(id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,storagedata OBJECT NOT NULL,storagetype INTEGER NOT NULL,purpose INTEGER NOT NULL,lastchanged BIGINT DEFAULT 0 NOT NULL,UNIQUE(purpose));


