# Update script db version 55 to db version 56
# $Author: heller $
# $Revision: 1.2 $
#
#
#POSTGRES does NOT create an index on secondary keys
CREATE INDEX idx_partnerevent_partnerid ON partnerevent(partnerid)
CREATE INDEX idx_certificates_partnerid ON certificates(partnerid)
CREATE INDEX idx_partnersystem_partnerid ON partnersystem(partnerid)
CREATE INDEX idx_httpheader_partnerid ON httpheader(partnerid)


