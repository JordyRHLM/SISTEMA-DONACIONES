package donaciones.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardStatsResponse {
    private BigDecimal totalDonations;
    private long activeCampaigns;
    private long totalVolunteers;
    private long organizationsHelped;
}