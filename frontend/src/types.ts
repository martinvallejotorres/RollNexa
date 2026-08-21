export type User={id:number;username:string;avatarUrl?:string;level:number};
export type RoomCard={id:number;name:string;description:string;gameSystem:string;campaignType:'ONE_SHOT'|'SHORT_CAMPAIGN'|'LONG_CAMPAIGN';tags:string[];joinMode:'OPEN'|'APPROVAL_REQUIRED';memberCount:number;maxParticipants:number;gm:string;member:boolean};
export type Member={id:number;username:string;avatarUrl?:string;level:number;role:'GM'|'PLAYER'};
export type RoomDetail={room:RoomCard;members:Member[];currentRole?:'GM'|'PLAYER'};
export type Message={id:number;user:User;content:string;sentAt:string};
export type Session={id:number;title:string;scheduledAt:string;status:'PROPOSED'|'CONFIRMED'|'CANCELLED';available:number;total:number;myResponse?:boolean};
export type Availability={id:number;date:string;startTime?:string;endTime?:string;available:boolean;user:User};
export type Page<T>={content:T[];number:number;totalPages:number;last:boolean;totalElements:number};

