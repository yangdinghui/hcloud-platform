package hcloud.platform.modules.plalog.service;

import hcloud.platform.modules.plalog.model.dto.PlaLogListDto;
import hcloud.platform.modules.plalog.model.vo.PlaLogSaveVo;

import java.util.List;

/**
 * description 描述这个类的主要功能、用途
 * 创建时间 2019/9/1
 *
 * @author 杨丁辉
 */
public interface PlaLogService {

    boolean save(PlaLogSaveVo vo);

    List<PlaLogListDto> list();
}
