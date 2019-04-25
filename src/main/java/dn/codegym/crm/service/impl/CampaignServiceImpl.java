package dn.codegym.crm.service.impl;

import dn.codegym.crm.constants.AppConsts;
import dn.codegym.crm.dto.CampaignDTO;
import dn.codegym.crm.entity.Campaign;
import dn.codegym.crm.repository.CampaignRepository;
import dn.codegym.crm.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    @Override
    public Iterable<Campaign> findAllByDeletedIsFalse() {
        return campaignRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Campaign> searchName(String name) {
        return campaignRepository.findAllByNameContaining(name);
    }

    @Override
    public void create(CampaignDTO campaignDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConsts.STRING_TO_DATE_FORMAT);
        LocalDate startDay = LocalDate.parse(campaignDTO.getStart_day(), formatter);
        LocalDate endDay = LocalDate.parse(campaignDTO.getEnd_day(), formatter);
        Campaign campaign=new Campaign();
        campaign.setName(campaignDTO.getName());
        campaign.setDescription(campaignDTO.getDescription());
        campaign.setEnd_day(endDay);
        campaign.setStart_day(startDay);
        campaign.setPhone_number(campaignDTO.getPhone_number());
        campaign.setDeleted(Boolean.FALSE);

        campaignRepository.save(campaign);
    }

    @Override
    public void update(CampaignDTO campaignDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConsts.STRING_TO_DATE_FORMAT);
        LocalDate startDay = LocalDate.parse(campaignDTO.getStart_day(), formatter);
        LocalDate endDay = LocalDate.parse(campaignDTO.getEnd_day(), formatter);

        Campaign campaign = campaignRepository.findById(campaignDTO.getId()).orElse(null);
        campaign.setName(campaignDTO.getName());
        campaign.setPhone_number(campaignDTO.getPhone_number());
        campaign.setDescription(campaignDTO.getDescription());
        campaign.setStart_day(startDay);
        campaign.setEnd_day(endDay);

        campaignRepository.save(campaign);
    }

    @Override
    public void delete(String id) {
        Campaign campaign=campaignRepository.findById(id).orElse(null);
        campaign.setDeleted(true);
        campaignRepository.save(campaign);
    }

    @Override
    public CampaignDTO findById(String id) {
        Campaign campaign = campaignRepository.findById(id).orElse(null);
        if (campaign != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConsts.STRING_TO_DATE_FORMAT);
            String startDay = campaign.getStart_day().format(formatter);
            String endDay = campaign.getEnd_day().format(formatter);

            CampaignDTO campaignDTO = new CampaignDTO();
            campaignDTO.setId(campaign.getId());
            campaignDTO.setName(campaign.getName());
            campaignDTO.setStart_day(startDay);
            campaignDTO.setEnd_day(endDay);
            campaignDTO.setPhone_number(campaign.getPhone_number());
            campaignDTO.setDescription(campaign.getDescription());
            campaignDTO.setDeleted(campaign.isDeleted());

            return campaignDTO;
        }
        return null;
    }
}
