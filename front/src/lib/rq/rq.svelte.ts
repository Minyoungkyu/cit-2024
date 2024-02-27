import { goto } from '$app/navigation';

import type { components, paths } from '$lib/types/api/v1/schema';
import type { Page } from '@sveltejs/kit';
import createClient from 'openapi-fetch';

import toastr from 'toastr';
import 'toastr/build/toastr.css';

toastr.options = {
  showDuration: 300,
  hideDuration: 300,
  timeOut: 3000,
  extendedTimeOut: 1000
};

class Rq {
  public member: components['schemas']['MemberDto'];

  public SITE_NAME: String = "코드이썬";

  constructor() {
    this.member = this.makeReactivityMember();
  }

  public effect(fn: () => void) {
    $effect(fn);
  }

  public isAdmin() {
    if (this.isLogout()) return false;

    return this.member.authorities.includes('ROLE_CLASSADMIN');
  }

  public isSystemAdmin() {
    if (this.isLogout()) return false;

    return this.member.authorities.includes('ROLE_SYSTEMADMIN');
  }

  public isSuperAdmin() {
    if (this.isLogout()) return false;

    return this.member.authorities.includes('ROLE_SUPERADMIN');
  }

  public isAdmPage($page: Page<Record<string, string>>) {
    return $page.url.pathname.startsWith('/adm/');
  }

  public isUsrPage($page: Page<Record<string, string>>) {
    return !this.isAdmPage($page);
  }

  // URL
  public goTo(url: string) {
    goto(url);
  }

  public replace(url: string) {
    goto(url, { replaceState: true });
  }

  public reload() {
    this.replace('/redirect?url=' + window.location.href);
  }

  // API END POINTS
  public apiEndPoints() {
    return createClient<paths>({
      baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
      credentials: 'include'
    });
  }

  // MSG, REDIRECT
  public msgAndRedirect(
    data: { msg: string } | undefined,
    error: { msg: string } | undefined,
    url: string,
    callback?: () => void
  ) {
    if (data) {
      this.msgInfo(data.msg);
      this.replace(url);
    }
    if (error) {
      this.msgError(error.msg);
    }

    if (callback) window.setTimeout(callback, 100);
  }

  public msgInfo(message: string) {
    toastr.info(message);
  }

  public msgError(message: string) {
    toastr.error(message);
  }

  // 인증
  // 이렇게 member 를 만들면 좋은 점이 있다.
  // member 의 값이 바뀌면, member 를 사용하는 모든 곳에서 자동으로 즉각 반영된다.
  public makeReactivityMember() {
    let id = $state(0);
    let name = $state('');
    let username = $state('');
    let createDate = $state('');
    let modifyDate = $state('');
    let cellphoneNo = $state('');
    let authorities: string[] = $state([]);

    let player = $state({
      id: 0,
      createDate: '',
      modifyDate: '',
      nickname: ''
    });

    return {
      get id() {
        return id;
      },
      set id(value: number) {
        id = value;
      },
      get createDate() {
        return createDate;
      },
      set createDate(value: string) {
        createDate = value;
      },
      get modifyDate() {
        return modifyDate;
      },
      set modifyDate(value: string) {
        modifyDate = value;
      },
      get username() {
        return username;
      },
      set username(value: string) { 
        username = value;
      },
      get name() {
        return name;
      },
      set name(value: string) {
        name = value;
      },
      get cellphoneNo() {
        return cellphoneNo;
      },
      set cellphoneNo(value: string) {
        cellphoneNo = value;
      },
      get authorities() {
        return authorities;
      },
      set authorities(value: string[]) {
        authorities = value;
      },
      get player() {
        return player;
      },
      set player(value: components['schemas']['PlayerDto']) {
        player.id = value.id;
        player.createDate = value.createDate;
        player.modifyDate = value.modifyDate;
        player.nickname = value.nickname;
        
      }
    };
  }

  public getAuthToString() {
    return this.member.authorities.map(authority => {
      switch (authority) {
        case 'ROLE_CLASSADMIN':
          return '학급관리자'; 
        case 'ROLE_SYSTEMADMIN':
          return '사업관리자'; 
        case 'ROLE_SUPERADMIN':
          return '최고관리자'; 
        default: 
          return '';
      }
    }).filter(Boolean);
  }


  

  public setLogined(member: components['schemas']['MemberDto']) {
    Object.assign(this.member, member);
  }

  public setLogout() {
    this.member.id = 0;
    this.member.createDate = '';
    this.member.modifyDate = '';
    this.member.name = '';
    this.member.username = '';
    this.member.cellphoneNo = '';
    this.member.authorities = [];	
    this.member.player = { id: 0, createDate: '', modifyDate: '', nickname: ''};
  }

  public isLogin() {
    return this.member.id !== 0;
  }

  public isLogout() {
    return !this.isLogin();
  }

  public async initAuth() {
    const { data } = await this.apiEndPoints().GET('/api/v1/members/me');

    if (data) {
      this.setLogined(data.data.item);
    }
  }

  public async logoutAndRedirect(url: string) {
    await this.apiEndPoints().POST('/api/v1/members/logout');

    this.setLogout();
    this.replace(url);
  }


}

const rq = new Rq();

export default rq;