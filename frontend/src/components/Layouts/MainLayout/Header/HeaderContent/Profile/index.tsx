import {useRef, useState} from 'react';

// material-ui
import {useTheme} from '@mui/material/styles';
import {
    Avatar,
    Box,
    CardContent,
    ClickAwayListener,
    Paper,
    Popper,
    Stack,
    Tab,
    Tabs,
    Typography
} from '@mui/material';

// project import
import ProfileTab from './ProfileTab';
import MainCard from '@components/Cards/MainCard';
import Transitions from '@components/@extended/Transitions';
import {useAppSelector} from "@redux/hook";

// tab panel wrapper
function TabPanel({children, value, index}: {
    children: React.ReactNode;
    value: number;
    index: number;
}) {
    return (
        <div role="tabpanel" hidden={value !== index} id={`profile-tabpanel-${index}`}
             aria-labelledby={`profile-tab-${index}`}>
            {value === index && children}
        </div>
    );
}

function a11yProps(index: number) {
    return {
        id: `profile-tab-${index}`,
        'aria-controls': `profile-tabpanel-${index}`
    };
}

// ==============================|| HEADER CONTENT - PROFILE ||============================== //

const Profile = () => {
    const theme = useTheme();
    const user = useAppSelector(state => state.user);

    const anchorRef = useRef(null);
    const [open, setOpen] = useState(false);
    const handleToggle = () => {
        setOpen((prevOpen) => !prevOpen);
    };

    const [value, setValue] = useState(0);

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    const iconBackColorOpen = theme.palette.mode === "dark" ? 'grey.200' : 'grey.300';

    return (
        <Box sx={{flexShrink: 0, ml: 0.75}}>
            <Stack direction={"row"} gap={2}>
                <Box
                    sx={{
                        p: 0.25,
                        bgcolor: open ? iconBackColorOpen : 'transparent',
                        borderRadius: 1,
                        '&:hover': {bgcolor: theme.palette.mode === "dark" ? 'secondary.light' : 'secondary.lighter'},
                        '&:focus-visible': {
                            outline: `2px solid ${theme.palette.secondary.dark}`,
                            outlineOffset: 2
                        },
                        cursor: 'pointer'
                    }}
                    aria-label="open profile"
                    ref={anchorRef}
                    aria-controls={open ? 'profile-grow' : undefined}
                    aria-haspopup="true"
                    onClick={handleToggle}
                >
                    <Stack direction="row" spacing={2} alignItems="center" sx={{p: 0.5}}>
                        <Stack>
                            <Typography variant="h6">{user.name}</Typography>
                        </Stack>
                        <Avatar src={undefined}/>
                    </Stack>
                </Box>
                <Popper
                    placement="bottom-end"
                    open={open}
                    anchorEl={anchorRef.current}
                    role={undefined}
                    transition
                    container={anchorRef.current}
                    popperOptions={{
                        modifiers: [
                            {
                                name: 'offset',
                                options: {
                                    offset: [0, 9]
                                }
                            }
                        ]
                    }}
                >
                    {({TransitionProps}) => (
                        <Transitions type="grow" position="top-right" in={open} {...TransitionProps}>
                            <Paper
                                sx={{
                                    boxShadow: theme.customShadows.z1,
                                    width: 290,
                                    minWidth: 240,
                                    maxWidth: 290,
                                    [theme.breakpoints.down('md')]: {
                                        maxWidth: 250
                                    }
                                }}
                            >
                                <ClickAwayListener onClickAway={() => {
                                    setOpen(false);
                                }}>
                                    <MainCard elevation={0} border={false} content={false}>
                                        <CardContent sx={{px: 2.5, pt: 3}}>
                                            <Stack direction="row" spacing={1.25} alignItems="center">
                                                <Avatar sx={{width: 32, height: 32}} src={undefined}/>
                                                <Stack>
                                                    <Typography variant="h6">{user.name}</Typography>
                                                </Stack>
                                            </Stack>
                                        </CardContent>

                                        <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                                            <Tabs variant="fullWidth" value={value} onChange={handleChange}
                                                  aria-label="profile tabs">
                                                <Tab
                                                    sx={{
                                                        display: 'flex',
                                                        flexDirection: 'row',
                                                        justifyContent: 'center',
                                                        alignItems: 'center',
                                                        textTransform: 'capitalize'
                                                    }}
                                                    icon={<i className="fas fa-user"
                                                             style={{marginBottom: 0, marginRight: '10px'}}/>}
                                                    label="Perfil"
                                                    {...a11yProps(0)}
                                                />
                                            </Tabs>
                                        </Box>
                                        <TabPanel value={value} index={0}>
                                            <ProfileTab/>
                                        </TabPanel>
                                    </MainCard>
                                </ClickAwayListener>
                            </Paper>
                        </Transitions>
                    )}
                </Popper>

            </Stack>
        </Box>
    );
};

export default Profile;
