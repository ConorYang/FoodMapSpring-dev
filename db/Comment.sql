USE [gourmetmap]
GO

/****** Object:  Table [dbo].[Comments]    Script Date: 2025/10/20 下午 02:02:20 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Comments](
	[CommentID] [int] IDENTITY(1,1) NOT NULL,
	[OrderDetailID] [int] NOT NULL,
	[Delicious] [bit] NOT NULL,
	[Quick] [bit] NOT NULL,
	[Friendly] [bit] NOT NULL,
	[Photogenic] [bit] NOT NULL,
	[TopCP] [bit] NOT NULL,
	[CommentContext] [nvarchar](500) NULL,
	[Photo1] [varbinary](max) NULL,
	[Photo2] [varbinary](max) NULL,
	[Photo3] [varbinary](max) NULL,
	[CreateAt] [datetime] NOT NULL,
 CONSTRAINT [PK_Comments_1] PRIMARY KEY CLUSTERED 
(
	[CommentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

