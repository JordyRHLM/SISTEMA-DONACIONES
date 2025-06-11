package donaciones.service.impl;

import donaciones.dto.response.DashboardStatsResponse;
import donaciones.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DonacionService donacionService;
    private final CampaniaService campaniaService;
    private final ColaboracionService colaboracionService;

    @Override
    public DashboardStatsResponse getDashboardStats() {
        return DashboardStatsResponse.builder()
                .totalDonations(donacionService.getTotalConfirmedDonations())
                .activeCampaigns(campaniaService.countActiveCampaigns())
                .totalVolunteers(colaboracionService.countTotalVolunteers())
                .organizationsHelped(campaniaService.countOrganizationsWithActiveCampaigns())
                .build();
    }
}