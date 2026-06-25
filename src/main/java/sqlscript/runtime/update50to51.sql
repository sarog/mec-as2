#
# Update script db version 50 to db version 51
# Postgres does not generate an automatic index for foreign keys
# $Author: heller $
# $Revision: 1.2 $
#
#POSTGRES does NOT create an index on secondary keys
CREATE INDEX idx_mdn_relatedmessageid ON mdn(relatedmessageid)


