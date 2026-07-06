#
# Update script db version 52 to db version 53
# Generate a table for the montly throughput of the system
# $Author: heller $
# $Revision: 1.2 $
#
#Missing indicies for tables
#
CREATE INDEX idx_messagelog_timestamputc ON messagelog(timestamputc);
CREATE INDEX idx_sendorder_nextexecutiontime on sendorder(nextexecutiontime);
CREATE INDEX idx_sendorder_orderstate on sendorder(orderstate);
