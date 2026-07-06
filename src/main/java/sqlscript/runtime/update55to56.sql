#
# Update script db version 55 to db version 56
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE messagelog ADD COLUMN sequenceno BIGINT DEFAULT 0 NOT NULL
DROP INDEX idx_messagelog_timestamputc
CREATE INDEX idx_messagelog_messageid_time_seq ON messagelog(messageid, timestamputc, sequenceno)