import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Drawer from '@material-ui/core/Drawer';
import MenuItem from '@material-ui/core/MenuItem';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';

import {Link as RouterLink} from'react-router-dom';
import Link from '@material-ui/core/Link';

const styles = {
    root: {
        flexGrow: 1,
    },
    menuButton: {
        marginRight: 'auto'
    },
};

class AppShell extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            toggle: false
        };
    }

    handleDrawerToggle = () => this.setState({toggle: !this.state.toggle})

    render() {
        const { classes } = this.props;
        return (
            <div>
                <div className={classes.root}>

                    <AppBar position="static">
                        <IconButton
                            className={classes.menuButton}
                            color="inherit"
                            onClick={this.handleDrawerToggle}>
                            <MenuIcon/>
                        </IconButton>
                    </AppBar>

                    <Drawer open={this.state.toggle}>

                        <Link component={RouterLink} to="/">
                            <MenuItem onClick={this.handleDrawerToggle}>
                                   홈 화면
                            </MenuItem>
                        </Link>

                        <Link component={RouterLink} to="/texts">
                            <MenuItem onClick={this.handleDrawerToggle}>
                                    텍스트 관리
                            </MenuItem>
                        </Link>

                        <Link component={RouterLink} to="/words">
                            <MenuItem onClick={this.handleDrawerToggle}>
                                단어 관리
                            </MenuItem>
                        </Link>
                    </Drawer>

                </div>

                <div id="content" style={{margin:'auto', marginTop: '20px'}}>
                    {React.cloneElement(this.props.children)}
                </div>

            </div>
        );
    }
}

export default withStyles(styles)(AppShell);

