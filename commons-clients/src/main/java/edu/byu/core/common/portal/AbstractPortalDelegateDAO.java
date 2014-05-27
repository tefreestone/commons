package edu.byu.core.common.portal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletRequest;
import java.util.Map;

/**
 * @author tef2
 */
abstract class AbstractPortalDelegateDAO {
    protected Logger LOG = LoggerFactory.getLogger(AbstractPortalDelegateDAO.class);

    //
//    @Autowired
//    @Qualifier("identityProService")
//    private IdentityService identityService;
//
    protected final String getPersonId(final PortletRequest request) {
        final String delegate = request.getParameter("delegate");
        final Map<String, String> userInfo = (Map<String, String>) request.getAttribute(PortletRequest.USER_INFO);
//        if (delegate != null) {
        return userInfo.get("personId");
//        }
//        } else {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Delegate : " + request.getRemoteUser());
//            }
//            return identityService.findCompletePersonByNetId(request.getRemoteUser()).getPersonId();
//        }
    }

}
